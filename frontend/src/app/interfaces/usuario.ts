import { SerieUsuario } from "./serieUsuario";

export interface Usuario {
	nombre: string,
	seriesTerminadas: { [id: number]: SerieUsuario };
	seriesPendientes: { [id: number]: SerieUsuario };
	seriesEmpezadas: { [id: number]: SerieUsuario };
}
