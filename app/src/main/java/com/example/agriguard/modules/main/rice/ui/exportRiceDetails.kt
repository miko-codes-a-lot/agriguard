package com.example.agriguard.modules.main.rice.ui

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.agriguard.modules.main.rice.model.dto.RiceInsuranceDto
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

fun exportRiceDetails(
    context: Context,
    data: RiceInsuranceDto,
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

        val titleParagraph = Paragraph("Agri Guard - Rice Insurance Report", titleFont)
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

        val insuranceDetails = listOf(
            "Status" to (data.status ?: "Pending"),
            "Fill Up Date" to dateFormatContainer(data.fillUpDate),
            "Lender" to (data.lender ?: ""),
            "New" to if (data.new) "Yes" else "No",
            "Renewal" to if (data.renewal) "Yes" else "No",
            "Self-Financed" to if (data.selfFinanced) "Yes" else "No",
            "Borrowing" to if (data.borrowing) "Yes" else "No",
            "IP Tribe" to (data.ipTribe ?: ""),
            "Street" to (data.street ?: ""),
            "Barangay" to (data.barangay ?: ""),
            "Municipality" to (data.municipality ?: ""),
            "Province" to (data.province ?: ""),
            "Bank Name" to (data.bankName ?: ""),
            "Bank Address" to (data.bankAddress ?: ""),
            "Male" to if (data.male) "Yes" else "No",
            "Female" to if (data.female) "Yes" else "No",
            "Single" to if (data.single) "Yes" else "No",
            "Married" to if (data.married) "Yes" else "No",
            "Widow" to if (data.widow) "Yes" else "No",
            "Beneficiary Name" to (data.nameOfBeneficiary ?: ""),
            "Beneficiary Age" to (data.age ?: ""),
            "Beneficiary Relationship" to (data.relationship ?: ""),
            "Sitio" to (data.sitio ?: ""),
            "Farm Location Barangay" to (data.farmLocationBarangay ?: ""),
            "Farm Location Municipality" to (data.farmLocationMunicipality ?: ""),
            "Farm Location Province" to (data.farmLocationProvince ?: ""),
            "North" to (data.north ?: ""),
            "South" to (data.south ?: ""),
            "East" to (data.east ?: ""),
            "West" to (data.west ?: ""),
            "Variety" to (data.variety ?: ""),
            "Plating Method" to (data.platingMethod ?: ""),
            "Date of Sowing" to dateFormatContainer(data.dateOfSowing),
            "Date of Planting" to dateFormatContainer(data.dateOfPlanting),
            "Date of Harvest" to dateFormatContainer(data.dateOfHarvest),
            "Land Category" to (data.landOfCategory ?: ""),
            "Soil Types" to (data.soilTypes ?: ""),
            "Topography" to (data.topography ?: ""),
            "Source of Irrigation" to (data.sourceOfIrrigations ?: ""),
            "Tenurial Status" to (data.tenurialStatus ?: ""),
            "Rice" to if (data.rice) "Yes" else "No",
            "Multi-Risk" to if (data.multiRisk) "Yes" else "No",
            "Natural" to if (data.natural) "Yes" else "No",
            "Amount of Cover" to (data.amountOfCover ?: ""),
            "Premium" to (data.premium ?: ""),
            "CLTI/ADSS" to (data.cltiAdss ?: ""),
            "Sum Insured" to (data.sumInsured ?: ""),
            "Wet Season" to if (data.wet) "Yes" else "No",
            "Dry Season" to if (data.dry) "Yes" else "No",
            "CIC No" to (data.cicNo ?: ""),
            "Date CIC Issued" to dateFormatContainer(data.dateCicIssued),
            "COC No" to (data.cocNo ?: ""),
            "Date COC Issued" to dateFormatContainer(data.dateCocIssued),
            "Period of Cover" to (data.periodOfCover ?: ""),
            "From" to (data.from ?: ""),
            "To" to (data.to ?: "")
        )

//        val labels = userDetails + insuranceDetails
        val labels = insuranceDetails

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

        val signatureTable = PdfPTable(1)
        signatureTable.widthPercentage = 100f

        signatureTable.addCell(createSignatureCell("Signature/Thumb Mark Over Printed Name", "Farmer Applicant", labelFont, valueFont))
        signatureTable.addCell(createSignatureCell("Signature Over Printed Name", "Supervising Agriculture Technologist", labelFont, valueFont))
        signatureTable.addCell(createDateCell("Date", labelFont, valueFont))

        document.add(signatureTable)


        addPageNumbers(writer)

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