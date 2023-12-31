package hu.simplexion.z2.browser.demo.table


import hu.simplexion.z2.browser.css.p0
import hu.simplexion.z2.browser.demo.strings
import hu.simplexion.z2.browser.html.Z2
import hu.simplexion.z2.browser.immaterial.table.table
import hu.simplexion.z2.browser.layout.surfaceContainer
import hu.simplexion.z2.browser.layout.surfaceContainerLow
import hu.simplexion.z2.browser.material.em
import hu.simplexion.z2.browser.material.px

fun Z2.tableDemo() =
    surfaceContainerLow(p0) {
        style.paddingTop = 16.px
        class Row(
            val v1: String,
            val v2: Int
        )

        val data = (1..50).map {
            Row("s-$it", it)
        }

        surfaceContainer(p0, scroll = false) {
            style.background = "transparent"

            table<Row> {
                title {
                   text = strings.tableTitle
                }
                search = true
                add = true
                export = true

                rowId = { it.v1 }
                query = { data }

                column {
                    label = strings.headerA
                    render = { text { it.v1 } }
                    comparator = { a, b -> a.v2.compareTo(b.v2) }
                    initialSize = 10.em
                }

                column {
                    label = strings.headerB
                    render = { text { it.v2 } }
                    comparator = { a, b -> a.v2.compareTo(b.v2) }
                }

            }
        }
    }