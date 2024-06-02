import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TarjetaSerieComponent } from './tarjeta-serie.component';

describe('TarjetaSerieComponent', () => {
  let component: TarjetaSerieComponent;
  let fixture: ComponentFixture<TarjetaSerieComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TarjetaSerieComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TarjetaSerieComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
