import { TestBed } from '@angular/core/testing';

import { FilmsDAOService } from './films-dao.service';

describe('FilmsDaoService', () => {
  let service: FilmsDAOService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FilmsDAOService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
