package com.example.uniforacademia

import com.google.firebase.firestore.DocumentId
import com.google.firebase.Timestamp

data class Aluno(
    @DocumentId
    var id: String? = null,
    val nome: String? = null,
    val matricula: String? = null,
    val curso: String? = null,
    val email: String? = null,
    val tipoUsuario: String? = null,
    val dataCadastro: Timestamp? = null,
)