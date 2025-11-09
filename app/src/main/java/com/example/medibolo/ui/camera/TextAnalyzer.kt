package com.example.medibolo.ui.camera


import android.content.Context
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class TextAnalyzer(
    private val context: Context,
    private val onTextDetected: (String) -> Unit // callback to ViewModel or UI
) : ImageAnalysis.Analyzer {
    companion object {
        private const val TAG = "TextAnalyzer"
    }

    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    private var lastAnalyzedTime = 0L

    override fun analyze(imageProxy: ImageProxy) {
        val currentTime = System.currentTimeMillis()

        // Process 1 frame per second (to save power)
        if (currentTime - lastAnalyzedTime < 1000) {
            imageProxy.close()
            return
        }
        lastAnalyzedTime = currentTime

        val mediaImage = imageProxy.image ?: run {
            imageProxy.close()
            return
        }

        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                val detectedText = visionText.text
                if (detectedText.isNotBlank()) {
                    Log.d(TAG, "Detected text: $detectedText")
                    onTextDetected(detectedText)
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "OCR failed: ${e.localizedMessage}")
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }
}
