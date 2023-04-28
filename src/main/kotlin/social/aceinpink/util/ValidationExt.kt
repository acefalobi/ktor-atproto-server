package social.aceinpink.util

fun String.isValidEmail(): Boolean {
    return Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$").matches(this)
}

fun String.isValidHandle(): Boolean {
    return Regex("^[a-zA-Z0-9][a-zA-Z0-9_.]+[a-zA-Z0-9]\$").matches(this)
}