// "Add non-null asserted (::f!!) call" "true"
class Foo {
    fun f() = 1
}

fun Foo?.test() {
    val f = ::f<caret>
}

// FUS_QUICKFIX_NAME: org.jetbrains.kotlin.idea.quickfix.AddExclExclCallFix
// FUS_K2_QUICKFIX_NAME: org.jetbrains.kotlin.idea.quickfix.AddExclExclCallFix