import { PersonalSerie } from "./personalSerie"

export interface Serie {
	id: number
	titulo: string
	sinopsis: string
	creadores: PersonalSerie[]
	actores: PersonalSerie[]
}
