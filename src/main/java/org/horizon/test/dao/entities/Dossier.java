package org.horizon.test.dao.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "dossiers")
public class Dossier {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDossier;

    @NotNull
    @Size(min = 5, max = 5)
    private String codeExploitant;

    @OneToOne(mappedBy = "dossier")
    private Identification identification;

    @OneToOne(mappedBy = "dossier")
    private Coordonnee coordonnee;

    @OneToOne(mappedBy = "dossier")
    private EER eer;

    @OneToOne(mappedBy = "dossier")
    private Banque banque;


}
