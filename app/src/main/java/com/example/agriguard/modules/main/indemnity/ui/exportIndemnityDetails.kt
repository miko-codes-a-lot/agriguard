package com.example.agriguard.modules.main.indemnity.ui

import android.content.Context
import android.os.Environment
import android.util.Log
import com.example.agriguard.modules.main.indemnity.model.dto.IndemnityDto
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

fun exportOnionDetails(
    context: Context,
    data: IndemnityDto,
//    user: UserDto,
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

        val titleParagraph = Paragraph("Agri Guard - Indemnity Report", titleFont)
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
        val indemnityDetails = mutableListOf(
            "Status" to (data.status ?: ""),
            "Crops" to (data.crops ?: ""),
            "Fill Update" to dateFormatContainer(data.fillUpdate),
            "Date of Birth" to dateFormatContainer(data.dateOfBirth)
        ).apply {
            addConditional("Regular" to "Yes", data.regular)
            addConditional("Punla" to "Yes", data.punla)
            addConditional("Cooperative Rice" to "Yes", data.cooperativeRice)
            addConditional("RSBSA" to "Yes", data.rsbsa)
            addConditional("SIKAT" to "Yes", data.sikat)
            addConditional("APCPC" to "Yes", data.apcpc)
            addAll(
                listOfNotNull(
                    data.others?.let { "Others" to it },
                    data.variety?.let { "Variety" to it },
                    data.causeOfLoss?.let { "Cause of Loss" to it },
                    data.causeOfDamage?.let { "Cause of Damage" to it },
                    data.dateOfLoss.takeIf { it.isNotEmpty() }?.let { "Date of Loss" to dateFormatContainer(it) },
                    data.dateOfPlanting.takeIf { it.isNotEmpty() }?.let { "Date of Planting" to dateFormatContainer(it) },
                    data.ageCultivation?.let { "Age Cultivation" to it },
                    data.areaDamaged?.let { "Area Damaged" to it },
                    data.others?.let { "Other" to it},
                    data.degreeOfDamage?.let { "Degree of Damage" to it },
                    data.expectedDateOfHarvest.takeIf { it.isNotEmpty() }?.let { "Expected Date of Harvest" to dateFormatContainer(it) },
                    data.insuredArea?.let { "Insured Area" to it },
                    data.north?.let { "North" to it },
                    data.south?.let { "South" to it },
                    data.east?.let { "East" to it },
                    data.west?.let { "West" to it },
                    data.upaSaGawaBilang?.let { "UPA Sa Gawa Bilang" to it },
                    data.upaSaGawaHalaga?.let { "UPA Sa Gawa Halaga" to it },
                    data.binhiBilang?.let { "Binhi Bilang" to it },
                    data.binhiHalaga?.let { "Binhi Halaga" to it },
                    data.abonoBilang?.let { "Abono Bilang" to it },
                    data.abonoHalaga?.let { "Abono Halaga" to it },
                    data.kemikalBilang?.let { "Kemikal Bilang" to it },
                    data.kemikalHalaga?.let { "Kemikal Halaga" to it },
                    data.patubigBilang?.let { "Patubig Bilang" to it },
                    data.patubigHalaga?.let { "Patubig Halaga" to it },
                    data.ibapaBilang?.let { "Ibapa Bilang" to it },
                    data.ibapaHalaga?.let { "Ibapa Halaga" to it },
                    data.kabuuanBilang?.let { "Kabuuan Bilang" to it },
                    data.kabuuanHalaga?.let { "Kabuuan Halaga" to it },
                )
            )
        }

//        val labels = userDetails + indemnityDetails
        val labels = indemnityDetails

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

        signatureTable.addCell(
            createSignatureCell(
                "Signature Over Printed Name",
                "(Name Of Assured Farmer)",
                labelFont,
                valueFont
            )
        )
        signatureTable.addCell(
            createDateCell(
                "Date",
                labelFont,
                valueFont
            )
        )

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
    cell.paddingBottom = 30f
    cell.paddingTop = 20f
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
