package studio.forface.freshtv.localdata

/** @return a [String] escaping all the `/` from the origin. Replaces '/' with '\/' */
fun String.escapeForRegex() = this.replace("/", """\/""")