package br.com.fiap.airquality.ui.theme


data class Estado(val nome: String, val cidades: List<String>)

object Estados {
    val listaEstados = listOf(
        Estado("Acre", listOf("Brasileia", "Cruzeiro do Sul", "Feijo")),
        Estado("Alagoas", listOf("Maceio")),
        Estado("Amapa", listOf("Macapa")),
        Estado("Amazonas", listOf("Benjamin Constant", "Manaus", "Tefe")),
        Estado("Bahia", listOf("Camacari")),
        Estado("Federal District", listOf("Brasilia")),
        Estado("Maranhao", listOf("Acailandia", "Santa Luzia")),
        Estado("Mato Grosso", listOf("Santo Antonio do Leverger", "Sinop")),
        Estado("Minas Gerais", listOf("Timoteo")),
        Estado("Para", listOf("Altamira", "Marituba", "Santarem")),
        Estado("Paraiba", listOf("Pianco")),
        Estado("Parana", listOf("Almirante Tamandare", "Curitiba", "Paranavai")),
        Estado("Pernambuco", listOf("Recife")),
        Estado("Rio de Janeiro", listOf("Rio de Janeiro")),
        Estado("Rondonia", listOf("Cacoal", "Porto Velho")),
        Estado("Roraima", listOf("Amajari", "Boa Vista")),
        Estado("Santa Catarina", listOf("Timbo")),
        Estado("Sao Paulo", listOf("Sao Paulo", "Campinas", "Ribeirao Preto")),
        Estado("Tocantins", listOf("Palmas"))
    )
}

