package app.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "user")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null

    @Column(name = "chat_id", nullable = false, unique = true)
    var chatId: String = ""
}