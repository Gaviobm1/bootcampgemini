import { TestBed } from '@angular/core/testing';

import { ContactosDaoService } from './contactos-dao.service';

describe('ContactosDaoService', () => {
  let service: ContactosDaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ContactosDaoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
