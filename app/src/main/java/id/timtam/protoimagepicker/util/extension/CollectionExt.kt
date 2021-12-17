package id.timtam.protoimagepicker.util.extension

@JvmName("arraySafeRemoveAt")
fun <T> ArrayList<T>.safeRemoveAt(position: Int) {
    if (position <= lastIndex) {
        removeAt(position)
    }
}

@JvmName("collectionSafeRemoveAt")
fun <T> MutableCollection<T>.safeRemoveAt(position: Int) {
    if (position <= (size-1)) {
        remove(elementAt(position))
    }
}