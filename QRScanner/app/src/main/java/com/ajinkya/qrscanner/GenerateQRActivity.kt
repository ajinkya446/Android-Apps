package com.ajinkya.qrscanner

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Color as AndroidColor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ajinkya.qrscanner.ui.theme.QRScannerTheme
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class GenerateQRActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QRScannerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GenerateQRScreen(onBackClick = { finish() })
                }
            }
        }
    }
}

data class QRStyle(
    val primaryColor: Color,
    val backgroundColor: Color,
    val hasGradient: Boolean,
    val gradientColor: Color,
    val hasLogo: Boolean,
    val cornerRadius: Float,
    val errorCorrection: ErrorCorrectionLevel
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun GenerateQRScreen(onBackClick: () -> Unit) {
    var text by remember { mutableStateOf("") }
    var qrBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // Style options
    var selectedColorIndex by remember { mutableIntStateOf(0) }
    var hasGradient by remember { mutableStateOf(true) }
    var hasLogo by remember { mutableStateOf(true) }
    var selectedStyleIndex by remember { mutableIntStateOf(1) }

    val colorSchemes = listOf(
        ColorScheme(
            name = "Classic",
            primary = Color(0xFF000000),
            background = Color(0xFFFFFFFF),
            gradient = Color(0xFF333333)
        ),
        ColorScheme(
            name = "Ocean",
            primary = Color(0xFF0066CC),
            background = Color(0xFFF0F8FF),
            gradient = Color(0xFF00CCFF)
        ),
        ColorScheme(
            name = "Sunset",
            primary = Color(0xFFFF6B35),
            background = Color(0xFFFFF5F0),
            gradient = Color(0xFFFFA500)
        ),
        ColorScheme(
            name = "Forest",
            primary = Color(0xFF2E7D32),
            background = Color(0xFFF1F8E9),
            gradient = Color(0xFF66BB6A)
        ),
        ColorScheme(
            name = "Berry",
            primary = Color(0xFF8E24AA),
            background = Color(0xFFF3E5F5),
            gradient = Color(0xFFE040FB)
        ),
        ColorScheme(
            name = "Midnight",
            primary = Color(0xFF1A237E),
            background = Color(0xFFE8EAF6),
            gradient = Color(0xFF3949AB)
        )
    )

    val cornerStyles = listOf(
        CornerStyle("Square", 0f),
        CornerStyle("Rounded", 0.3f),
        CornerStyle("Smooth", 0.5f)
    )

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Create QR Code",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // QR Preview Card
            AnimatedVisibility(
                visible = qrBitmap != null,
                enter = fadeIn() + scaleIn(initialScale = 0.8f)
            ) {
                qrBitmap?.let { bitmap ->
                    ModernQRDisplay(bitmap = bitmap)
                }
            }

            if (qrBitmap == null) {
                EmptyQRPreview()
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Text Input
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Enter text or URL") },
                placeholder = { Text("https://example.com or any text...") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                trailingIcon = {
                    if (text.isNotEmpty()) {
                        IconButton(onClick = { text = "" }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear"
                            )
                        }
                    }
                },
                singleLine = false,
                minLines = 2,
                maxLines = 4,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Color Selection
            Text(
                text = "Color Theme",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                colorSchemes.forEachIndexed { index, scheme ->
                    ColorChip(
                        scheme = scheme,
                        isSelected = selectedColorIndex == index,
                        onClick = { selectedColorIndex = index }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Style Options
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Gradient Toggle
                StyleToggleChip(
                    icon = Icons.Default.ColorLens,
                    label = "Gradient",
                    isSelected = hasGradient,
                    onClick = { hasGradient = !hasGradient },
                    modifier = Modifier.weight(1f)
                )

                // Logo Toggle
                StyleToggleChip(
                    icon = Icons.Default.QrCode,
                    label = "Logo",
                    isSelected = hasLogo,
                    onClick = { hasLogo = !hasLogo },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Corner Style
            Text(
                text = "Corner Style",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                cornerStyles.forEachIndexed { index, style ->
                    FilterChip(
                        selected = selectedStyleIndex == index,
                        onClick = { selectedStyleIndex = index },
                        label = { Text(style.name) },
                        modifier = Modifier.weight(1f),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Generate Button
            Button(
                onClick = {
                    if (text.isNotEmpty()) {
                        val scheme = colorSchemes[selectedColorIndex]
                        val cornerStyle = cornerStyles[selectedStyleIndex]
                        qrBitmap = generateModernQRCode(
                            text = text,
                            primaryColor = scheme.primary.toArgb(),
                            backgroundColor = scheme.background.toArgb(),
                            gradientColor = if (hasGradient) scheme.gradient.toArgb() else scheme.primary.toArgb(),
                            hasGradient = hasGradient,
                            hasLogo = hasLogo,
                            cornerRadius = cornerStyle.radius
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                enabled = text.isNotEmpty(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.QrCode,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = "Generate QR Code",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            // Action Buttons
            AnimatedVisibility(visible = qrBitmap != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    qrBitmap?.let { bitmap ->
                        IconButton(
                            onClick = { shareQRCode(context, bitmap) },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.secondaryContainer,
                                    shape = RoundedCornerShape(8.dp)
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share",
                                tint = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }

                        IconButton(
                            onClick = { saveQRCode(context, bitmap) },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.secondaryContainer,
                                    shape = RoundedCornerShape(8.dp)
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Download,
                                contentDescription = "Save",
                                tint = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

data class ColorScheme(
    val name: String,
    val primary: Color,
    val background: Color,
    val gradient: Color
)

data class CornerStyle(
    val name: String,
    val radius: Float
)

@Composable
fun ColorChip(
    scheme: ColorScheme,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable(onClick = onClick)
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (isSelected) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(scheme.primary, scheme.gradient)
                        )
                    )
            )
            Text(
                text = scheme.name,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
            )
        }
    }
}

@Composable
fun StyleToggleChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clickable(onClick = onClick)
            .clip(RoundedCornerShape(10.dp))
            .background(
                if (isSelected) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ModernQRDisplay(bitmap: Bitmap) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(260.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = BitmapPainter(bitmap.asImageBitmap()),
                    contentDescription = "Generated QR Code",
                    modifier = Modifier.size(240.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Scan to access",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun EmptyQRPreview() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.QrCode,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Your QR code will appear here",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
        }
    }
}

private fun generateModernQRCode(
    text: String,
    primaryColor: Int,
    backgroundColor: Int,
    gradientColor: Int,
    hasGradient: Boolean,
    hasLogo: Boolean,
    cornerRadius: Float
): Bitmap? {
    return try {
        val size = 768
        val hints = hashMapOf<EncodeHintType, Any>()
        hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
        hints[EncodeHintType.MARGIN] = 2

        val writer = MultiFormatWriter()
        val bitMatrix: BitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, size, size, hints)

        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // Draw background
        canvas.drawColor(backgroundColor)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        // Calculate gradient if enabled
        val gradientPaint = if (hasGradient) {
            Paint(Paint.ANTI_ALIAS_FLAG).apply {
                shader = android.graphics.LinearGradient(
                    0f, 0f, size.toFloat(), size.toFloat(),
                    primaryColor, gradientColor,
                    android.graphics.Shader.TileMode.CLAMP
                )
            }
        } else null

        // Draw QR modules with rounded corners
        val moduleWidth = size.toFloat() / bitMatrix.width
        val padding = moduleWidth * 0.15f

        for (x in 0 until bitMatrix.width) {
            for (y in 0 until bitMatrix.height) {
                if (bitMatrix[x, y]) {
                    val left = x * moduleWidth + padding
                    val top = y * moduleWidth + padding
                    val right = left + moduleWidth - padding * 2
                    val bottom = top + moduleWidth - padding * 2

                    val rect = RectF(left, top, right, bottom)
                    val cornerRadiusPx = (moduleWidth - padding * 2) * cornerRadius

                    canvas.drawRoundRect(rect, cornerRadiusPx, cornerRadiusPx,
                        gradientPaint ?: paint.apply { color = primaryColor })
                }
            }
        }

        // Add logo in center if enabled
        if (hasLogo) {
            val logoSize = (size * 0.22).toInt()
            val logoBitmap = createAppLogo(logoSize, primaryColor, backgroundColor)
            val logoX = (size - logoSize) / 2f
            val logoY = (size - logoSize) / 2f

            // Draw white background for logo
            val logoBgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            logoBgPaint.color = backgroundColor
            val logoBgRect = RectF(
                logoX - 8,
                logoY - 8,
                logoX + logoSize + 8,
                logoY + logoSize + 8
            )
            canvas.drawRoundRect(logoBgRect, 12f, 12f, logoBgPaint)

            // Draw logo
            canvas.drawBitmap(logoBitmap, logoX, logoY, null)
        }

        bitmap
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

private fun createAppLogo(size: Int, primaryColor: Int, backgroundColor: Int): Bitmap {
    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    // Draw rounded background
    val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    bgPaint.color = primaryColor
    val rect = RectF(0f, 0f, size.toFloat(), size.toFloat())
    canvas.drawRoundRect(rect, size * 0.2f, size * 0.2f, bgPaint)

    // Draw QR icon
    val iconPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    iconPaint.color = backgroundColor
    iconPaint.strokeWidth = size * 0.08f
    iconPaint.style = Paint.Style.STROKE
    iconPaint.strokeCap = Paint.Cap.ROUND

    val padding = size * 0.25f
    val boxSize = (size - padding * 2) / 3

    // Draw simplified QR pattern
    // Top-left position marker
    val markerPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    markerPaint.color = backgroundColor
    markerPaint.style = Paint.Style.FILL

    val positions = listOf(
        Pair(0, 0), // Top-left marker
        Pair(2, 0), // Top-right
        Pair(0, 2), // Bottom-left
    )

    positions.forEach { (cx, cy) ->
        val x = padding + cx * boxSize + boxSize / 2
        val y = padding + cy * boxSize + boxSize / 2
        canvas.drawCircle(x, y, boxSize * 0.3f, markerPaint)
    }

    // Center dot
    val centerX = size / 2f
    val centerY = size / 2f
    canvas.drawCircle(centerX, centerY, boxSize * 0.25f, markerPaint)

    return bitmap
}

private fun shareQRCode(context: android.content.Context, bitmap: Bitmap) {
    try {
        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs()
        val file = File(cachePath, "qr_code.png")

        val stream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.flush()
        stream.close()

        val uri = androidx.core.content.FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )

        val shareIntent = android.content.Intent().apply {
            action = android.content.Intent.ACTION_SEND
            type = "image/png"
            putExtra(android.content.Intent.EXTRA_STREAM, uri)
            putExtra(android.content.Intent.EXTRA_TEXT, "Scan this QR code!")
            addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(
            android.content.Intent.createChooser(shareIntent, "Share QR Code")
        )
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

private fun saveQRCode(context: android.content.Context, bitmap: Bitmap) {
    try {
        val filename = "QR_${System.currentTimeMillis()}.png"
        val downloadsDir = android.os.Environment.getExternalStoragePublicDirectory(
            android.os.Environment.DIRECTORY_DOWNLOADS
        )
        val file = File(downloadsDir, filename)

        val stream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.flush()
        stream.close()

        // Notify media scanner
        android.media.MediaScannerConnection.scanFile(
            context,
            arrayOf(file.absolutePath),
            arrayOf("image/png"),
            null
        )

        android.widget.Toast.makeText(
            context,
            "Saved to Downloads: $filename",
            android.widget.Toast.LENGTH_SHORT
        ).show()
    } catch (e: Exception) {
        e.printStackTrace()
        android.widget.Toast.makeText(
            context,
            "Failed to save QR code",
            android.widget.Toast.LENGTH_SHORT
        ).show()
    }
}
