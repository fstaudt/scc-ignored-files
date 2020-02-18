package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {

    request {
        method 'GET'
        url('/api/ignored') {
            queryParameters {
                parameter 'first': 0
                parameter 'maxResult': 50
            }
        }
    }
    response {
        status 206
        body(
                []
        )
        headers { contentType(applicationJson()) }
    }
}
