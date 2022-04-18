/* eslint-disable prettier/prettier */
import { client } from "@/client/axiosClient";

class CertificateService {
    static getAliases() {
        return client({
            url: "api/certificates/getAliases",
            method: "GET",
        })
    }
    static getCertificate(alias) {
        return client({
            url: "api/certificates/getCertificate/" + alias,
            method: "POST"
        })
    }

    static createCertificate(certDTO) {
        return client({
            url: "api/certificates",
            method: "POST",
            data: certDTO
        })
    }

    static invalidateCertificate(certificateDTO) {
        return client({
            url: "api/certificates/revoke",
            method: "POST",
            data: certificateDTO
        })
    }

    static checkCertificate(alias) {
        return client({
            url: "api/certificates/validate/" + alias,
            method: "POST",
        })
    }

}
export default CertificateService;
