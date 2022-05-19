export interface UserRealEstateDTO {
    username: string
    realEstateId: number
    role: string
}

export interface UserRealEstateToViewDTO extends UserRealEstateDTO {
    realEstateName: string
}