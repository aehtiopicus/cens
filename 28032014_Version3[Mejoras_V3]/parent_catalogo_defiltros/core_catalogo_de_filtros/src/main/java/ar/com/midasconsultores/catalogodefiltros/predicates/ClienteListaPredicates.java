package ar.com.midasconsultores.catalogodefiltros.predicates;

import ar.com.midasconsultores.catalogodefiltros.domain.QClienteLista;

import com.mysema.query.types.Predicate;

public class ClienteListaPredicates {
	
	// String codigoCliente,
	public static Predicate codigoCliente(final String searchTerm) {
		QClienteLista qclienteLista = QClienteLista.clienteLista;
		return qclienteLista.codigoCliente.startsWithIgnoreCase(searchTerm);
//				.or(qclienteLista.codigoCliente.containsIgnoreCase(searchTerm));
	}
        
        public static Predicate vendedor(final String codigoVendedor){
            QClienteLista qclienteLista = QClienteLista.clienteLista;
            return qclienteLista.vendedor.codigoVendedor.eq(codigoVendedor);
        }
                
	
}
