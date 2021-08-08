package keywords

/**
 * File to use for testing keywords.
 * Some lines will produce errors, but keywords should work
 * @see https://kotlinlang.org/docs/keyword-reference.html
 */

import some.strange.import

@file:Deprecated

fun main() {
    0 as Int as? Int is Int !is Int
    for (i in 0 .. 10) continue
    do { break } while (true || false)
    if (0 in listOf(0, 1) || 1 !in listOf(10, 2)) null else return
    try {
        when (10) {
            in listOf(10, 2) -> ""
            !in listOf(11, 2) -> ""
            is Int -> ""
            !is Float -> throw Exception()
        }
    } catch (e: Exception) {
    } finally { }
    typeof
}

interface B
sealed open annotation const data enum abstract class object A
@param:Nullable class C(val b: B): B by b {
    tailrec suspend override operator public internal inner private noinline inline infix final external expect crossinline actual fun <reified out in T> foo(vararg i: Int): T where T : String = ""
    @receiver:Nullable @property:Nullable @delegate:Nullable @setparam:Nullable @get:Nullable @set:Nullable lateinit var d by lazy { "" }
        get() = field
        set(value) { field = value }

    constructor(d: dynamic)
    init {
        super()
        this()
    }

    companion object {}
}
typealias D = B
