import { Routes } from '@angular/router';
import { InicioComponent } from './vistas/inicio/inicio.component';
import { CatalogoComponent } from './vistas/catalogo/catalogo.component';

export const routes: Routes = [
	{ path: 'inicio', component: InicioComponent },
	{ path: 'catalogo', component: CatalogoComponent },
	{ path: '', redirectTo: '/inicio', pathMatch: 'full' },
	{ path: '**', redirectTo: '/inicio' }
];
