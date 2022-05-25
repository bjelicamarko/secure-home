export interface RealEstateDTO {
    name: string
}

export interface RealEstateWithDevicesDTO {
    name: string
    devices: string[]
}

export interface RealEstateToAssignDTO extends RealEstateDTO {
    id: number
}

export interface RealEstateWithPhotoAndRoleDTO extends RealEstateDTO {
    photo: String;
    role: String
}

export interface RealEstateWithHouseholdAndDevicesDTO {
    household: String[];
    devices: String[]
}