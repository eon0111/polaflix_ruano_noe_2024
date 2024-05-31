import { SerieUsuario } from "./serieUsuario";

export interface Usuario {
	nombre: string,
	seriesTerminadas: Map<number, SerieUsuario>,
	seriesPendientes: Map<number, SerieUsuario>,
	seriesEmpezadas: Map<number, SerieUsuario>
}
