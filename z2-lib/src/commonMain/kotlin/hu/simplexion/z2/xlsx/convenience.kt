package hu.simplexion.z2.xlsx

import hu.simplexion.z2.xlsx.model.XlsxCoordinate
import hu.simplexion.z2.xlsx.model.XlsxDocument
import hu.simplexion.z2.xlsx.model.XlsxSheet

/**
 * Save or download xlsx file.
 */
expect fun XlsxDocument.save(fileName: String)

/**
 * fill a row with the list, started at specified coordinate
 */
fun XlsxSheet.fillRow(coord: String, values: Iterable<Any?>) {

    val c = XlsxCoordinate(coord)

    val rn = c.rowNumber
    var cn = c.colNumber

    for (v in values) {
        this[cn, rn].value = v
        cn++
    }

}

/**
 * fill a table with list of lists, started at specified coordinate
 */
fun XlsxSheet.fillTable(coord: String, table: Iterable<Iterable<Any?>>) {

    val c = XlsxCoordinate(coord)

    var rn = c.rowNumber

    for (row in table) {
        var cn = c.colNumber
        for (v in row) {
            this[cn, rn].value = v
            cn++
        }
        rn++
    }

}
