package id.timtam.protoimagepicker.model.type

enum class CanvasType(val type: String, val ratio: String, val basis: Float) {
    SQUARE("square", "1:1", 1f),
    PORTRAIT("portrait", "4:5", 0.8f),
    LANDSCAPE("landscape", "16:9", 1.8f);

    companion object {
        fun getRatio(canvasType: CanvasType) =
            values().firstOrNull { it == canvasType }?.ratio ?: LANDSCAPE.ratio

        fun getTypeValue(type: String?) = values().firstOrNull { it.type == type } ?: LANDSCAPE
    }
}