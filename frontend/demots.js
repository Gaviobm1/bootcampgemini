var Circle = /** @class */ (function () {
    function Circle(radius) {
        this.radius = radius;
    }
    Circle.prototype.area = function () {
        return Circle.PI * Math.pow(this.radius, 2);
    };
    Circle.prototype.circumference = function () {
        return Circle.PI * this.diameter();
    };
    Circle.prototype.diameter = function () {
        return this.radius * 2;
    };
    Circle.PI = 3.141592654;
    return Circle;
}());
var DayOfWeek;
(function (DayOfWeek) {
    DayOfWeek["MONDAY"] = "Monday";
    DayOfWeek["TUESDAY"] = "Tuesday";
    DayOfWeek["WEDNESDAY"] = "Wednesday";
    DayOfWeek["THURSDAY"] = "Thursday";
    DayOfWeek["FRIDAY"] = "Friday";
    DayOfWeek["SATURDAY"] = "Saturday";
    DayOfWeek["SUNDAY"] = "Sunday";
})(DayOfWeek || (DayOfWeek = {}));
var today = DayOfWeek.THURSDAY;
var Genero;
(function (Genero) {
    Genero["M"] = "Masculino";
    Genero["F"] = "Femenino";
    Genero["O"] = "Otro";
})(Genero || (Genero = {}));
var Persona = /** @class */ (function () {
    function Persona(_genero, _nombre) {
        this._genero = _genero;
        this._nombre = _nombre;
    }
    Object.defineProperty(Persona.prototype, "nombre", {
        get: function () {
            return this._nombre;
        },
        set: function (name) {
            this._nombre = name;
        },
        enumerable: false,
        configurable: true
    });
    Object.defineProperty(Persona.prototype, "genero", {
        get: function () {
            return this._genero;
        },
        set: function (genero) {
            this._genero = genero;
        },
        enumerable: false,
        configurable: true
    });
    return Persona;
}());
var persona = new Persona(Genero.F, "Jackson");
console.log(persona.genero);
