interface Shape {
    area(): number;
}

class Circle implements Shape {
    static PI = 3.141592654;
    
    constructor(private radius: number) {}

    area(): number {
        return Circle.PI * this.radius ** 2;
    }

    circumference(): number {
        return Circle.PI * this.diameter();
    }

    diameter() {
        return this.radius * 2;
    }
}

enum DayOfWeek {
    MONDAY = "Monday",
    TUESDAY = "Tuesday",
    WEDNESDAY = "Wednesday",
    THURSDAY = "Thursday",
    FRIDAY = "Friday",
    SATURDAY = "Saturday", 
    SUNDAY = "Sunday"
}

const today: DayOfWeek = DayOfWeek.THURSDAY;

enum Genero {
    M = "Masculino",
    F = "Femenino",
    O = "Otro"
}

class Persona<T> {
    constructor(private _genero: T, private _nombre: string ) {}

    get nombre() {
        return this._nombre;
    }
    set nombre(name: string) {
        this._nombre = name;
    }

    get genero() {
        return this._genero;
    }
    set genero(genero: T) {
        this._genero = genero;
    }
}

const persona: Persona<Genero> = new Persona(Genero.F, "Jackson");
