package br.com.alura.alugames.principal

import br.com.alura.alugames.modelo.Gamer
import br.com.alura.alugames.modelo.Periodo
import br.com.alura.alugames.modelo.PlanoAssinatura
import br.com.alura.alugames.modelo.PlanoAvuso
import br.com.alura.alugames.servico.ConsumoApi
import com.google.gson.GsonBuilder
import com.sun.tools.javac.Main
import java.io.File
import java.time.LocalDate

fun main() {
    val consumo = ConsumoApi()
    val listaGamer = consumo.buscaGamers()
    val listaJogoJson = consumo.buscaJogosJson()

    //println(listaGamer)

    val gamerCaroline = listaGamer.get(3)
    val jogoResidentEvil = listaJogoJson.get(10)
    val jogoSpider = listaJogoJson.get(13)
    val jogoTheLastOfUs = listaJogoJson.get(2)

    val jogoDandara = listaJogoJson.get(5)
    val jogoAssassins = listaJogoJson.get(4)
    val jogoCyber = listaJogoJson.get(6)
    val jogoGod = listaJogoJson.get(7)
    val jogoSkyrim = listaJogoJson.get(18)

    ///println(gamerCaroline)
    //println(jogoResidentEvil)

    val periodo1 = Periodo(LocalDate.now(), LocalDate.now().plusDays(7))
    val periodo2 = Periodo(LocalDate.now(), LocalDate.now().plusDays(3))
    val periodo3 = Periodo(LocalDate.now(), LocalDate.now().plusDays(10))
    val periodo4 = Periodo(LocalDate.of(2023,8,2), LocalDate.of(2023,8,15))

    gamerCaroline.alugaJogo(jogoResidentEvil, periodo1)
    gamerCaroline.alugaJogo(jogoSpider, periodo2)
    gamerCaroline.alugaJogo(jogoTheLastOfUs, periodo3)
    gamerCaroline.alugaJogo(jogoSpider, periodo4)

    //println(gamerCaroline.jogosAlugados)
    //println(gamerCaroline.jogosDoMes(8))

    val gamerCamila = listaGamer.get(5)
    gamerCamila.plano = PlanoAssinatura("PRATA", 9.90, 3, 0.15)
    gamerCamila.alugaJogo(jogoResidentEvil, periodo1)
    gamerCamila.alugaJogo(jogoSpider, periodo2)
    gamerCamila.alugaJogo(jogoTheLastOfUs, periodo3)
    gamerCamila.alugaJogo(jogoTheLastOfUs, periodo3)

    gamerCamila.recomendarJogo(jogoTheLastOfUs, 10)
    gamerCamila.recomendarJogo(jogoAssassins, 8)
    gamerCamila.recomendarJogo(jogoCyber, 7)
    gamerCamila.recomendarJogo(jogoGod, 10)
    gamerCamila.recomendarJogo(jogoDandara, 8)
    gamerCamila.recomendarJogo(jogoSkyrim, 8)
    gamerCamila.recomendarJogo(jogoSpider, 6)

    //println(gamerCamila.jogosAlugados)

    gamerCamila.recomendar(7)
    gamerCamila.recomendar(10)
    gamerCamila.recomendar(8)
    //println(gamerCamila)

    gamerCamila.alugaJogo(jogoResidentEvil, periodo1)
    //println(gamerCamila.jogosAlugados)

    gamerCamila.recomendarJogo(jogoResidentEvil, 7)
    gamerCamila.recomendarJogo(jogoTheLastOfUs, 10)

    gamerCaroline.recomendarJogo(jogoResidentEvil, 8)
    gamerCaroline.recomendarJogo(jogoTheLastOfUs, 9)

    println("Recomendações da Camila")
    println(gamerCamila.jogosRecomendados)
    println("Recomendações da Caroline")
    println(gamerCaroline.jogosRecomendados)

    val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    val serializacao = gson.toJson(gamerCamila.jogosRecomendados)
    println(serializacao)

    val arquivo = File("JogosRecomendados-${gamerCamila.nome}.json")
    arquivo.writeText(serializacao)
    println(arquivo.absolutePath)

    println(gamerCamila)
}
