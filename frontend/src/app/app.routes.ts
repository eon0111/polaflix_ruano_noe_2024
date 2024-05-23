import { Routes } from '@angular/router';
import { InicioComponent } from './inicio/inicio.component';
import { CatalogoComponent } from './catalogo/catalogo.component';
import { FacturacionComponent } from './facturacion/facturacion.component';

export const routes: Routes = [
	{ path: 'inicio', component: InicioComponent },
	{ path: 'catalogo', component: CatalogoComponent },
	{ path: 'facturacion', component: FacturacionComponent },
	{ path: '', redirectTo: '/inicio', pathMatch: 'full' },
	{ path: '**', redirectTo: '/inicio' }
];
