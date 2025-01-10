package com.example.agriguard.modules.main.complain.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Base64
import android.util.Log
import com.example.agriguard.modules.main.complain.model.dto.ComplaintInsuranceDto
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.Font
import com.itextpdf.text.FontFactory
import com.itextpdf.text.Image
import com.itextpdf.text.PageSize
import com.itextpdf.text.Paragraph
import com.itextpdf.text.Phrase
import com.itextpdf.text.pdf.ColumnText
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

fun exportComplaintDetails (
    context: Context,
    data: ComplaintInsuranceDto,
    user: UserDto,
    onFinish: (file: File) -> Unit,
    onError: (Exception) -> Unit
) {
    val titleFont: Font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18f)
    val labelFont: Font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12f)
    val valueFont: Font = FontFactory.getFont(FontFactory.HELVETICA, 12f)

    try {
        val file = createFile(context)
        val document = Document(PageSize.A4)
        val writer = PdfWriter.getInstance(document, FileOutputStream(file))
        writer.setFullCompression()
        document.open()

        val titleParagraph = Paragraph("Agri Guard - Complaint Report", titleFont)
        titleParagraph.alignment = Element.ALIGN_CENTER
        document.add(titleParagraph)
        addLineSpace(document, 2)

        val table = PdfPTable(2)
        table.widthPercentage = 100f
        table.setWidths(floatArrayOf(3f, 6f))

        data.imageBase64?.let { base64Image ->
            val image = decodeBase64ToPdfImage(base64Image)
            if (image != null) {
                val pageWidth = document.pageSize.width - document.leftMargin() - document.rightMargin()
                image.scaleAbsolute(pageWidth, 395f)
                image.alignment = Element.ALIGN_CENTER
                document.add(image)
            } else {
                val noImageParagraph = Paragraph("No Image Provided", valueFont)
                noImageParagraph.alignment = Element.ALIGN_CENTER
                document.add(noImageParagraph)
            }
        }

        addLineSpace(document, 2)

        val userDetails = listOfNotNull(
            "First Name" to user.firstName,
            user.middleName?.let { "Middle Name" to it },
            "Last Name" to user.lastName,
            user.mobileNumber?.let { "Mobile Number" to it },
            user.address?.let { "Address" to it }
        )

        val complaintDetails = listOf(
            "Status" to (data.status ?: "Pending"),
            "Rice" to if (data.rice) "Yes" else "No",
            "Onion" to if (data.onion) "Yes" else "No",
            "Variety" to (data.variety ?: "N/A"),
            "Area Damage" to (data.areaDamage ?: "N/A"),
            "Degree of Damage" to (data.degreeOfDamage ?: "N/A"),
            "Cause of Damage" to (data.causeOfDamage ?: "N/A")
        )

        val labels = userDetails + complaintDetails

        for ((label, value) in labels) {
            val labelCell = PdfPCell(Phrase("$label ", labelFont))
            labelCell.border = PdfPCell.NO_BORDER
            labelCell.horizontalAlignment = Element.ALIGN_LEFT
            table.addCell(labelCell)

            val valueCell = PdfPCell(Phrase(value, valueFont))
            valueCell.border = PdfPCell.BOTTOM
            valueCell.horizontalAlignment = Element.ALIGN_LEFT
            valueCell.paddingBottom = 5f
            valueCell.paddingTop = 5f
            table.addCell(valueCell)
        }

        document.add(table)

        addLineSpace(document, 3)

        if (!user.validId.isNullOrEmpty()) {
            document.newPage()
            val validIdTitle = Paragraph("Valid ID", titleFont)
            validIdTitle.alignment = Element.ALIGN_CENTER
            document.add(validIdTitle)
            addLineSpace(document, 2)

            val validIdImage = decodeBase64ToPdfImage(user.validId!!)
            if (validIdImage != null) {
                validIdImage.scaleToFit(document.pageSize.width - 80f, document.pageSize.height - 200f)
                validIdImage.alignment = Image.ALIGN_CENTER
                document.add(validIdImage)
            } else {
                val noValidIdParagraph = Paragraph("No Valid ID Provided", valueFont)
                noValidIdParagraph.alignment = Element.ALIGN_CENTER
                document.add(noValidIdParagraph)
            }
        }

        document.close()
        onFinish(file)


    } catch (e: Exception) {
        onError(e)
    }
}

fun addLineSpace(document: Document, lines: Int) {
    for (i in 0 until lines) {
        document.add(Paragraph(" "))
    }
}

fun createFile(context: Context): File {
    val directory = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
    if (!directory?.exists()!!) {
        directory.mkdirs()
    }
    val fileName = "Agri Guard.pdf"
    val file = File(directory, fileName)
    Log.d("PDFCreation", "File path: ${file.absolutePath}")
    if (file.exists()) {
        Log.d("PDFCreation", "File already exists, deleting...")
        file.delete()
    }
    return file
}

fun addPageNumbers(writer: PdfWriter) {
    val totalPages = writer.pageNumber - 1
    val cb = writer.directContentUnder

    for (i in 1..totalPages) {
        val pageSize = writer.pageSize
        ColumnText.showTextAligned(
            cb, Element.ALIGN_CENTER,
            Phrase("Page $i of $totalPages", FontFactory.getFont(FontFactory.HELVETICA, 10f)),
            (pageSize.left + pageSize.right) / 2, pageSize.bottom + 20, 0f
        )
    }
}
fun decodeBase64ToPdfImage(base64: String): Image? {
    return try {
        val decodedBytes = Base64.decode(base64, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val byteArray = stream.toByteArray()
        Image.getInstance(byteArray)
    } catch (e: Exception) {
        null
    }
}