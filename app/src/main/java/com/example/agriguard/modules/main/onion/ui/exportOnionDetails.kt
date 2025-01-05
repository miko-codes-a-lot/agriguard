package com.example.agriguard.modules.main.onion.ui

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.agriguard.modules.main.onion.model.dto.OnionInsuranceDto
import com.example.agriguard.modules.main.user.model.dto.UserDto
import com.itextpdf.text.Chunk
import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.Font
import com.itextpdf.text.FontFactory
import com.itextpdf.text.PageSize
import com.itextpdf.text.Paragraph
import com.itextpdf.text.Phrase
import com.itextpdf.text.pdf.ColumnText
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Locale

fun exportOnionDetails(
    context: Context,
    data: OnionInsuranceDto,
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

        val titleParagraph = Paragraph("Agri Guard - Onion Insurance Report", titleFont)
        titleParagraph.alignment = Element.ALIGN_CENTER
        document.add(titleParagraph)
        addLineSpace(document, 2)

        val table = PdfPTable(2)
        table.widthPercentage = 100f
        table.setWidths(floatArrayOf(3f, 6f))

//        val userDetails = listOfNotNull(
//            "First Name" to user.firstName,
//            user.middleName?.let { "Middle Name" to it },
//            "Last Name" to user.lastName,
//            user.mobileNumber?.let { "Mobile Number" to it },
//            user.address?.let { "Address" to it }
//        )

        val insuranceDetails = mutableListOf(
            "Status" to (data.status ?: "Pending"),
            "Fill Up Date" to dateFormatContainer(data.fillUpDate)
        ).apply {
            addConditional("Male" to "Yes", data.male)
            addConditional("Female" to "Yes", data.female)

            when {
                data.single -> add("Single" to "Yes")
                data.married -> add("Married" to "Yes")
                data.widow -> add("Widow" to "Yes")
            }

            addAll(
                listOfNotNull(
                    data.nameOfBeneficiary?.let { "Beneficiary Name" to it },
                    data.ageOfBeneficiary?.let { "Beneficiary Age" to it },
                    data.relationshipOfBeneficiary?.let { "Beneficiary Relationship" to it },
                    data.farmLocation?.let { "Farm Location" to it },
                    data.area?.let { "Area" to it },
                    data.soilType?.let { "Soil Type" to it },
                    data.soilPh?.let { "Soil pH" to it },
                    data.topography?.let { "Topography" to it },
                    data.variety?.let { "Variety" to it },
                    data.dateOfPlanting.takeIf { it.isNotEmpty() }?.let { "Date of Planting" to dateFormatContainer(it) },
                    data.estdDateOfHarvest.takeIf { it.isNotEmpty() }?.let { "Estimated Harvest Date" to dateFormatContainer(it) },
                    data.ageGroup?.let { "Age Group" to it },
                    data.noOfHills?.let { "Number of Hills" to it },
                    data.typeOfIrrigation?.let { "Irrigation Type" to it },
                    data.averageYield?.let { "Average Yield" to it },
                    data.landPreparation?.let { "Land Preparation" to it },
                    data.materialsItem?.let { "Materials Item" to it },
                    data.materialsQuantity?.let { "Materials Quantity" to it },
                    data.materialsCost?.let { "Materials Cost" to it },
                    data.laborWorkForce?.let { "Labor Work Force" to it },
                    data.laborQuantity?.let { "Labor Quantity" to it },
                    data.laborCost?.let { "Labor Cost" to it },
                    data.totalCoast?.let { "Total Cost" to it },
                    data.farmLocationSitio?.let { "Farm Location Sitio" to it },
                    data.farmLocatioBarangay?.let { "Farm Location Barangay" to it },
                    data.farmLocationMunicipality?.let { "Farm Location Municipality" to it },
                    data.farmLocationProvince?.let { "Farm Location Province" to it },
                    data.north?.let { "North" to it },
                    data.south?.let { "South" to it },
                    data.east?.let { "East" to it },
                    data.west?.let { "West" to it }
                )
            )
        }

//        val labels = userDetails + insuranceDetails
        val labels =  insuranceDetails

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

        com.example.agriguard.modules.main.rice.ui.addLineSpace(document, 3)

        val signatureTable = PdfPTable(1)
        signatureTable.widthPercentage = 100f

        signatureTable.addCell(createSignatureCell("Signature of Proposer", "", labelFont, valueFont))
        signatureTable.addCell(createSignatureCell("Name and Signature of Supervising PT", "", labelFont, valueFont))
        signatureTable.addCell(createDateCell("Date", labelFont, valueFont))

        document.add(signatureTable)


        addPageNumbers(writer)
        document.close()
        onFinish(file)

    } catch (e: Exception) {
        onError(e)
    }
}

fun MutableList<Pair<String, String>>.addConditional(entry: Pair<String, String>, condition: Boolean) {
    if (condition) add(entry)
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

fun openFile(context: Context, file: File) {
    val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, "application/pdf")
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NO_HISTORY
    }
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "No PDF viewer installed", Toast.LENGTH_SHORT).show()
    }
}

fun formatDate(date: String?, inputFormat: String = "yyyy-MM-dd", outputFormat: String = "dd-MM-yyyy"): String? {
    return try {
        date?.let {
            val inputFormatter = SimpleDateFormat(inputFormat, Locale.getDefault())
            val parsedDate = inputFormatter.parse(it)

            parsedDate?.let { dateObject ->
                val outputFormatter = SimpleDateFormat(outputFormat, Locale.getDefault())
                outputFormatter.format(dateObject)
            }
        }
    } catch (e: Exception) {
        null
    }
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


private fun createSignatureCell(title: String, subtitle: String, labelFont: Font, valueFont: Font): PdfPCell {
    val paragraph = Paragraph()
    paragraph.add(Chunk("____________________________________________________", valueFont))
    paragraph.add(Chunk("\n$title\n", labelFont))
    paragraph.add(Chunk(subtitle, valueFont))

    val cell = PdfPCell(paragraph)
    cell.border = PdfPCell.NO_BORDER
    cell.horizontalAlignment = Element.ALIGN_CENTER
    cell.paddingBottom = 20f
    cell.paddingTop = 10f
    return cell
}

private fun createDateCell(title: String, labelFont: Font, valueFont: Font): PdfPCell {
    val paragraph = Paragraph()
    paragraph.add(Chunk("____________________________________________________", valueFont))
    paragraph.add(Chunk("\n$title", labelFont))

    val cell = PdfPCell(paragraph)
    cell.border = PdfPCell.NO_BORDER
    cell.horizontalAlignment = Element.ALIGN_CENTER
    cell.paddingBottom = 20f
    cell.paddingTop = 10f
    return cell
}