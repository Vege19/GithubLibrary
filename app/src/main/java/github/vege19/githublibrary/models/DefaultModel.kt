package github.vege19.githublibrary.models

data class DefaultModel(val count: Int = 0,
                        val next: String = "",
                        val previous: Int = 0,
                        val results: List<Pokemon>)

data class Pokemon(val name: String = "",
                           val url: String = "")