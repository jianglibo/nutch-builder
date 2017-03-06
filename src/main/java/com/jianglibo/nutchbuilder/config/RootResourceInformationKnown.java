package com.jianglibo.nutchbuilder.config;

import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.rest.core.mapping.ResourceMetadata;

/**
 * @author jianglibo@gmail.com
 *
 */
public class RootResourceInformationKnown /* extends RootResourceInformation*/ {

    /**
     * @param metadata
     * @param entity
     * @param invoker
     */
    public RootResourceInformationKnown(ResourceMetadata metadata, PersistentEntity<?, ?> entity/*, RepositoryInvoker invoker*/) {
//        super(metadata, entity, invoker);
    }
    
//    private RootResourceInformation rri;
//    
//
//    /**
//     * @param metadata
//     * @param entity
//     * @param invoker
//     */
//    public RootResourceInformationKnown(ResourceMetadata metadata, PersistentEntity<?, ?> entity, RepositoryInvoker invoker) {
//        this.rri = new RootResourceInformation(metadata, entity, invoker);
//    }
//    
//    
//	public void verifySupportedMethod(HttpMethod httpMethod, ResourceType resourceType) throws HttpRequestMethodNotSupportedException, ResourceNotFoundException {
//	    this.rri.verifySupportedMethod(httpMethod, resourceType);
//	}
//	
//	public RepositoryInvoker getInvoker() {
//	    return rri.getInvoker();
//	}
//	
//	public ResourceMetadata getResourceMetadata() {
//	    return rri.getResourceMetadata();
//	}
//	public Class<?> getDomainType() {
//	    return rri.getDomainType();
//	}
}
