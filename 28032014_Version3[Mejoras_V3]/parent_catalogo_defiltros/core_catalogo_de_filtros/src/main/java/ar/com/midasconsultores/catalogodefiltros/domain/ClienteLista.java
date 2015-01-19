package ar.com.midasconsultores.catalogodefiltros.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.jasypt.hibernate4.type.EncryptedStringType;

@Entity
@Table(name = "cliente_lista")
public class ClienteLista implements Serializable {

    private static final long serialVersionUID = -552820805692401247L;

    @Id
    @TableGenerator(name = "cliente_lista_generator", table = "generic_generator", pkColumnName = "sequence_name", valueColumnName = "sequence_value", pkColumnValue = "cliente_lista_gen", initialValue = 10000, allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "cliente_lista_generator")
    @Index(columnNames = "id", name = "cliente_lista_id_idx")
    private Long id;

    @Column(name = "codigo_cliente", unique = true)
    @Index(columnNames = "codigo_cliente", name = "cliente_lista_codigo_cliente_idx")
    private String codigoCliente;

    @ManyToOne(targetEntity = Lista.class, cascade = CascadeType.ALL)
    private Lista lista;

    @ManyToOne(targetEntity = Vendedor.class, cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName =  "codigo_vendedor")
    private Vendedor vendedor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public Lista getLista() {
        return lista;
    }

    public void setLista(Lista lista) {
        this.lista = lista;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

}
