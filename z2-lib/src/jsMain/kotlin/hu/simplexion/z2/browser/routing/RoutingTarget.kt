package hu.simplexion.z2.browser.routing

import hu.simplexion.z2.localization.icon.LocalizedIcon
import hu.simplexion.z2.localization.text.LocalizedText
import hu.simplexion.z2.util.UUID

interface RoutingTarget<R> {

    var parent: Router<R>?

    var relativePath: String

    val label: LocalizedText?

    val icon: LocalizedIcon?

    val parameters: MutableList<RouterParameter<*>>
        get() = mutableListOf()

    val loggedIn : Boolean

    var visibility: ((target: RoutingTarget<R>) -> Boolean)?

    fun accepts(path: List<String>): Boolean {
        return path.first() == relativePath
    }

    fun open() {
        root.open(this)
    }

    fun open(uuid: UUID<*>) {
        openWith(this, uuid)
    }

    fun open(receiver: R, path: List<String>)

    fun open(target: RoutingTarget<R>) {
        root.open(target)
    }

    fun openWith(target: RoutingTarget<R>, vararg parameters: Any) {
        root.openWith(target, *parameters)
    }

    fun absolutePath(withParameters: Boolean = true): MutableList<String> {
        val path = parent?.absolutePath() ?: mutableListOf()
        path += relativePath
        if (withParameters) path += parameters.map { it.valueOrNull?.toString() ?: "" }
        return path
    }

    val root: RoutingTarget<R>
        get() {
            var current = this
            while (current.parent != null) {
                current = current.parent !!
            }
            return current
        }

    fun visible() : Boolean {
        if (loggedIn && ! isLoggedIn) return false
        return visibility?.invoke(this) ?: true
    }
}