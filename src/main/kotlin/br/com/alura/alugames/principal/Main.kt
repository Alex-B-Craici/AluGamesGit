package br.com.alura.alugames.principal

import br.com.alura.alugames.modelo.Gamer
import br.com.alura.alugames.modelo.InfoJogo
import br.com.alura.alugames.modelo.Jogo
import br.com.alura.alugames.servico.ConsumoApi
import com.google.gson.Gson
import com.sun.tools.javac.Main
import transformarEmIdade
import java.util.*

fun main() {
    val leitura = Scanner(System.`in`)

    val gamer = Gamer.criarGame(leitura)
    println("Cadastro concluído com sucesso\n--Dados do gamer--\n$gamer")
    println("Idade do gamer: " + gamer.dataNascimento?.transformarEmIdade())

    do {
        println("Digite um código de jogo para buscar:")
        val busca = leitura.nextLine()

        val buscaApi = ConsumoApi()
        val json = buscaApi.buscaJogo(busca)

        var meuJogo: Jogo? = null

        val resultado = runCatching {
            val gson = Gson()
            val meuInfoJogo = gson.fromJson(json, InfoJogo::class.java)

            meuJogo = Jogo(
                meuInfoJogo.info.title,
                meuInfoJogo.info.thumb
            )
        }

        resultado.onFailure {
            println("br.com.alura.alugames.modelo.Jogo inexistente. Tente outra id")
        }

        resultado.onSuccess {
            println("Deseja inserir uma descrição personalizada? S/N")
            val opcao = leitura.nextLine()

            if (opcao.equals("s", true)) {
                println("Insira a opção personalizada para o jogo")
                val descricaoPersonalizada = leitura.nextLine()
                meuJogo?.descricao = descricaoPersonalizada
            } else {
                meuJogo?.descricao = meuJogo?.titulo
            }

            gamer.jogosBuscados.add(meuJogo)
        }

        println("Deseja buscar um novo jogo? S/N")
        val resposta  = leitura.nextLine()

    } while (resposta.equals("s", ignoreCase = true))

    println("****jogos buscados****\n" + gamer.jogosBuscados)

    println("Jogos ordenado por titulo")
    gamer.jogosBuscados.sortBy {
        it?.titulo
    }

    gamer.jogosBuscados.forEach {
        println("Titulo: " + it?.titulo)
    }

    val jogosFiltrados = gamer.jogosBuscados.filter {
        it?.titulo?.contains("batman", ignoreCase = true) ?: false
    }
    println("***Jogos filtrados***\n$jogosFiltrados")

    println("Deseja excluir algum jogo da lista? S/N")
    val opcao = leitura.nextLine()
    if (opcao.equals("s", ignoreCase = true)) {
        println(gamer.jogosBuscados)
        println("Informe a posição de deseja excluir")
        val posicao = leitura.nextInt()
        gamer.jogosBuscados.removeAt(posicao)
    }
    println("\n***Lista atualizada***\n${gamer.jogosBuscados}")

    println("Busca finalizada com sucesso")
}