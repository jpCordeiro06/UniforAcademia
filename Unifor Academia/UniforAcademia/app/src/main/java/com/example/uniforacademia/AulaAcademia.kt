package com.example.uniforacademia

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class AulaAcademia(
    var id: String = "", // ID do documento Firestore
    var nomeAula: String = "",
    var instrutorNome: String = "",
    var diasSemana: String = "", // Ex: "SEG,QUA,SEX" ou "TER,QUI"
    var horario: String = "",    // Ex: "18:00-19:00"
    var vagasMaximas: Int = 0,
    var vagasOcupadas: Int = 0, // Inicialmente 0, atualizado por agendamentos
    var descricao: String = "", // Opcional
    var status: String = "ativa", // "ativa", "cancelada", etc.
    @ServerTimestamp var dataCriacao: Date? = null,
    @ServerTimestamp var dataUltimaModificacao: Date? = null
)