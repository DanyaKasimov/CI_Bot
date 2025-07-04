package app.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "repos",
    uniqueConstraints = [UniqueConstraint(columnNames = ["userId", "repoLink"]) ])
class Repo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User = User()

    @Column(name = "repo_link", nullable = false, length = 255)
    var repoLink: String = ""
}