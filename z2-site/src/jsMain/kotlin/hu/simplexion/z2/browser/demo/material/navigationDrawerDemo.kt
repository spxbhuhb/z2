package hu.simplexion.z2.browser.demo.material

import hu.simplexion.z2.browser.demo.icons
import hu.simplexion.z2.browser.demo.strings
import hu.simplexion.z2.browser.html.Z2
import hu.simplexion.z2.browser.layout.surfaceContainerHigh
import hu.simplexion.z2.browser.material.navigation.NavigationItem
import hu.simplexion.z2.browser.material.navigation.drawerItem
import hu.simplexion.z2.browser.material.navigation.navigationDrawer

fun Z2.navigationDrawerDemo() =
    surfaceContainerHigh {
        navigationDrawer {
            drawerItem(NavigationItem(icons.inbox, strings.inbox))
            drawerItem(NavigationItem(icons.outbox, strings.outbox))
            drawerItem(NavigationItem(icons.favourites, strings.favourites))
            drawerItem(NavigationItem(icons.trash, strings.trash))
        }
    }