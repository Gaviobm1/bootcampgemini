const average = (...args) => {
  let rslt = 0;
  if (!args.length) {
    return rslt;
  }
  for (let num of args) {
    rslt += num;
  }
  return rslt / args.length;
};

const mates = {
  PI: 3.141592654,
  average: (...args) => {
    let rslt = 0;
    if (!args.length) {
      return rslt;
    }
    for (let num of args) {
      rslt += num;
    }
    return rslt / args.length;
  },
  add: (...args) => {
    let rslt = 0;
    for (let num of args) {
      rslt += num;
    }
    return rslt;
  },
  circleArea: function (r) {
    return this.PI * r ** 2;
  },
};

function MiClase(elId, elNombre) {
  const obj = this;
  this.id = elId;
  this.nombre = elNombre;
  this.muestraId = function () {
    alert("El ID del objeto es " + this.id);
  };
  this.ponNombre = (nom) => {
    obj.nombre = nom.toUpperCase();
  };
}

function createCounter() {
  let count = 0;

  return {
    increment: function () {
      count++;
    },
    decrement: function () {
      count--;
    },
    getCount: function () {
      return count;
    },
    resetCount: function () {
      count = 0;
    },
  };
}

class Rectangle {
  constructor(width, height) {
    this._width = width;
    this._height = height;
  }
  set width(width) {
    this._width = width;
  }
  get width() {
    return this._width;
  }
  set height(height) {
    this._height = height;
  }
  get height() {
    return this._height;
  }
  get area() {
    return this._width * this._height;
  }
}

function resolveAfter2Seconds(x) {
  return new Promise(
    (resolve) => {
      setTimeout(() => {
        resolve(x);
      }, 2000);
    },
    (reject) => {
      console.log("Rejected");
    }
  );
}

async function f1() {
  const x = await resolveAfter2Seconds(10);
  console.log(x);
}

f1();
