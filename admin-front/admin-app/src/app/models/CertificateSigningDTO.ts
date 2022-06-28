import { CertificateDataDTO } from "./CertificateDataDTO";
import { ExtendedKeyUsageDTO } from "./ExtendedKeyUsageDTO";
import { KeyUsageDTO } from "./KeyUsageDTO";

export interface CertificateSigningDTO {
    certificateDataDTO: CertificateDataDTO | null;
    keyUsageDTO: KeyUsageDTO | null
    extendedKeyUsageDTO: ExtendedKeyUsageDTO | null;
    ca: boolean | null;
}