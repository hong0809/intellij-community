// LANGUAGE_VERSION: 1.1
// IS_APPLICABLE: true
// WITH_STDLIB

class Owner(val z: Int) {
    fun foo(y: Int) = y + z
    val x = 7.let {<caret> foo(it) }
}

// IGNORE_K2

