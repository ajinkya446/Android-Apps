package com.ajinkya.qrscanner

import android.Manifest
import android.content.Intent
import android.graphics.ImageFormat
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.ajinkya.qrscanner.ui.theme.QRScannerTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors

class ScanQRActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QRScannerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScannerScreen(onBackClick = { finish() })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun ScannerScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = context as LifecycleOwner

    var scannedResult by remember { mutableStateOf<Barcode?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    LaunchedEffect(Unit) {
        cameraPermissionState.launchPermissionRequest()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Scan QR Code",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (cameraPermissionState.status) {
                is PermissionStatus.Granted -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        CameraPreview(
                            onBarcodeDetected = { barcode ->
                                if (!showDialog) {
                                    scannedResult = barcode
                                    showDialog = true
                                }
                            }
                        )

                        // Scan overlay
                        Card(
                            modifier = Modifier
                                .padding(32.dp)
                                .align(Alignment.BottomCenter),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.QrCodeScanner,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Align QR code within frame",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
                is PermissionStatus.Denied -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(24.dp)
                        ) {
                            Text(
                                text = "Camera permission required",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                                Text("Grant Permission")
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog && scannedResult != null) {
        ScanResultDialog(
            barcode = scannedResult!!,
            onDismiss = { showDialog = false },
            onContinue = {
                showDialog = false
                scannedResult = null
            }
        )
    }
}

@Composable
fun CameraPreview(onBarcodeDetected: (Barcode) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = context as LifecycleOwner
    val executor = remember { Executors.newSingleThreadExecutor() }

    val options = remember {
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
    }
    val scanner = remember { BarcodeScanning.getClient(options) }

    AndroidView(
        factory = { ctx ->
            val previewView = PreviewView(ctx)
            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                val imageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()

                imageAnalysis.setAnalyzer(executor) { imageProxy ->
                    val buffer = imageProxy.planes[0].buffer
                    val bytes = ByteArray(buffer.remaining())
                    buffer.get(bytes)

                    val inputImage = InputImage.fromByteArray(
                        bytes,
                        imageProxy.width,
                        imageProxy.height,
                        imageProxy.imageInfo.rotationDegrees,
                        ImageFormat.NV21
                    )

                    scanner.process(inputImage)
                        .addOnSuccessListener { barcodes ->
                            barcodes.firstOrNull()?.let { barcode ->
                                onBarcodeDetected(barcode)
                            }
                        }
                        .addOnCompleteListener {
                            imageProxy.close()
                        }
                }

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalysis
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, ContextCompat.getMainExecutor(ctx))

            previewView
        },
        modifier = Modifier.fillMaxSize()
    )

    DisposableEffect(Unit) {
        onDispose {
            executor.shutdown()
            scanner.close()
        }
    }
}

@Composable
fun ScanResultDialog(
    barcode: Barcode,
    onDismiss: () -> Unit,
    onContinue: () -> Unit
) {
    val context = LocalContext.current

    val (title, content, onAction) = remember(barcode) {
        when (barcode.valueType) {
            Barcode.TYPE_URL -> {
                Triple(
                    "URL Found",
                    barcode.rawValue ?: "",
                    {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(barcode.rawValue))
                        context.startActivity(intent)
                    }
                )
            }
            Barcode.TYPE_CONTACT_INFO -> {
                val contact = barcode.contactInfo
                val info = buildString {
                    contact?.name?.formattedName?.let { append("Name: $it\n") }
                    contact?.phones?.firstOrNull()?.number?.let { append("Phone: $it\n") }
                    contact?.emails?.firstOrNull()?.address?.let { append("Email: $it\n") }
                }
                Triple("Contact Found", info, null)
            }
            else -> {
                Triple("QR Code Content", barcode.rawValue ?: "", null)
            }
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                text = content,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        confirmButton = {
            TextButton(onClick = onContinue) {
                Text("Continue Scanning")
            }
        },
        dismissButton = if (onAction != null) {
            {
                TextButton(onClick = { onAction(); onDismiss() }) {
                    Text("Open")
                }
            }
        } else null
    )
}
