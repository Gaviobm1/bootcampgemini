import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ForumulariosComponent } from './forumularios.component';

describe('ForumulariosComponent', () => {
  let component: ForumulariosComponent;
  let fixture: ComponentFixture<ForumulariosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ForumulariosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ForumulariosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
