db = db.getSiblingDB('heymozo');
db.restaurantes.drop();
db.restaurantes.createIndex({"id":1});
db.restaurantes.insert({
            "id": 1,
            "nombre": "Bar-Resto 1980",
            "moneda": {
                "moneda": "$"
            },
            "latitud": -31.6366443,
            "longitud": -60.699605,
	    "direccion": "Blvd. Galvez 2281, S3000ABA Santa Fe",
	    "telefono": "0342 452-0309",
	    "pagina": "https://www.facebook.com/1980boulevard/",
	    "rating": 4.1
        });
db.restaurantes.insert({
            "id": 2,
            "nombre": "Paladar Negro",
            "moneda": {
                "moneda": "$"
            },
            "latitud": -31.6387288,
            "longitud": -60.6936089,
	    "direccion": "Sarmiento 3398, S3000 Santa Fe",
	    "telefono": "0342 456-2868",
	    "pagina": "https://www.facebook.com/paladaroriginal/",
	    "rating": 3.9
        });
db.cartas.drop();
db.cartas.createIndex({"id":1});
db.cartas.insert({
            "id": 1,
            "nombre_restaurant": {
                "nombre": "Bar-Resto 1980",
                "moneda": {
                    "moneda": "$"
                }
            },
            "secciones": [
                {
                    "id": 1,
                    "nombre": "Empanadas",
                    "productos": [
                        {
                            "id": 1,
                            "nombre": "Empanada de carne",
                            "precio": 12,
                            "descripcion_corta":"Saladas o dulces. Carne vacuna, con aceitunas negras."
                        },
                        {
                            "id": 2,
                            "nombre": "Empanada de verdura",
                            "precio": 12,
                            "descripcion_corta":"Acelga, huevo, morrón y aceitunas"
                        },
                        {
                            "id": 3,
                            "nombre": "Empanada de pollo",
                            "precio": 14,
                            "descripcion_corta":"Pollo, cebolla, morrón y pimiento."
                        },
                        {
                            "id": 4,
                            "nombre": "Empanada de jamón y queso",
                            "precio": 14,
                            "descripcion_corta":"Jamón cocido, queso muzzarella y huevo"
                        },
                        {
                            "id": 5,
                            "nombre": "Empanada árabe",
                            "precio": 18,
                            "descripcion_corta":"Cebolla, tomate y limón a gusto",
                            "descripcion_larga":"La gastronomía árabe es el conjunto de cocinas pertenecientes a los países árabes. Se puede decir que es el conjunto de comidas tradicionales a los ciudadanos de los Estados árabes del Golfo incluyendo todos los países de la Península Arábica, los países del norte de África cuya mayoría de idioma es el idioma árabe. La cocina árabe se puede ver fácilmente que es una mezcla equilibrada de características gastronómicas mediterráneas y de cocina India en el empleo de las especias."
                        }
                    ]
                },
                {
                    "id": 2,
                    "nombre": "Sandwiches",
                    "productos": [
                        {
                            "id": 1,
                            "nombre": "Sandwich triple",
                            "precio": 12,
                            "descripcion_corta":"Con jamón y queso",
                            "descripcion_larga":"sdgd"
                        },
                        {
                            "id": 2,
                            "nombre": "Sandwich triple",
                            "precio": 12
                        }   
                    ]
                },
                {
                    "id": 3,
                    "nombre": "Pizzas",
                    "productos": [
                        {
                            "id": 1,
                            "nombre": "Especial",
                            "precio": 122
                        },
                        {
                            "id": 2,
                            "nombre": "Napolitana",
                            "precio": 190
                        },
                        {
                            "id": 3,
                            "nombre": "Cebollada",
                            "precio": 180
                        },
                        {
                            "id": 4,
                            "nombre": "4 quesos",
                            "precio": 172,
                            "imagen": {"id":"1.1.1"}
                        },
                        {
                            "id": 5,
                            "nombre": "Especial",
                            "precio": 122
                        },
                        {
                            "id": 6,
                            "nombre": "Napolitana",
                            "precio": 190
                        },
                        {
                            "id": 7,
                            "nombre": "Cebollada",
                            "precio": 180
                        },
                        {
                            "id": 8,
                            "nombre": "4 quesos",
                            "precio": 172
                        },
                        {
                            "id": 9,
                            "nombre": "Especial",
                            "precio": 122
                        },
                        {
                            "id": 10,
                            "nombre": "Napolitana",
                            "precio": 190
                        },
                        {
                            "id": 11,
                            "nombre": "Cebollada",
                            "precio": 180
                        },
                        {
                            "id": 12,
                            "nombre": "4 quesos",
                            "precio": 172
                        },
                        {
                            "id": 13,
                            "nombre": "Especial",
                            "precio": 122
                        },
                        {
                            "id": 14,
                            "nombre": "Napolitana",
                            "precio": 190
                        },
                        {
                            "id": 15,
                            "nombre": "Cebollada",
                            "precio": 180
                        },
                        {
                            "id": 16,
                            "nombre": "4 quesos",
                            "precio": 172
                        }
                    ]
                },
                {
                    "id": 4,
                    "nombre": "Postres",
                    "productos": [
                        {
                            "id": 1,
                            "nombre": "Helado",
                            "precio": 56
                        },
                        {
                            "id": 2,
                            "nombre": "Torta alemana",
                            "precio": 56
                        },
                        {
                            "id": 3,
                            "nombre": "Tiramisú",
                            "precio": 56
                        },
                        {
                            "id": 4,
                            "nombre": "Frutas",
                            "precio": 56
                        },
                        {
                            "id": 5,
                            "nombre": "Helado",
                            "precio": 56
                        },
                        {
                            "id": 6,
                            "nombre": "Torta alemana",
                            "precio": 56
                        },
                        {
                            "id": 7,
                            "nombre": "Tiramisú",
                            "precio": 56
                        },
                        {
                            "id": 8,
                            "nombre": "Frutas",
                            "precio": 56
                        },
                        {
                            "id": 9,
                            "nombre": "Helado",
                            "precio": 56
                        },
                        {
                            "id": 10,
                            "nombre": "Torta alemana",
                            "precio": 56
                        },
                        {
                            "id": 11,
                            "nombre": "Tiramisú",
                            "precio": 56
                        },
                        {
                            "id": 12,
                            "nombre": "Frutas",
                            "precio": 56
                        }
                    ]
                },
                {
                    "id": 5,
                    "nombre": "Bebidas",
                    "productos": [
                        {
                            "id": 1,
                            "nombre": "Fernet",
                            "precio": 122
                        },
                        {
                            "id": 2,
                            "nombre": "Gancia",
                            "precio": 122
                        },
                        {
                            "id": 3,
                            "nombre": "Whisky",
                            "precio": 190
                        },
                        {
                            "id": 4,
                            "nombre": "Martini",
                            "precio": 142
                        },
                        {
                            "id": 5,
                            "nombre": "Jarra loca",
                            "precio": 200
                        },
                        {
                            "id": 6,
                            "nombre": "Fernet",
                            "precio": 122
                        },
                        {
                            "id": 7,
                            "nombre": "Gancia",
                            "precio": 122
                        },
                        {
                            "id": 8,
                            "nombre": "Whisky",
                            "precio": 190
                        },
                        {
                            "id": 9,
                            "nombre": "Martini",
                            "precio": 142
                        },
                        {
                            "id": 10,
                            "nombre": "Jarra loca",
                            "precio": 200
                        },
                        {
                            "id": 11,
                            "nombre": "Fernet",
                            "precio": 122
                        },
                        {
                            "id": 12,
                            "nombre": "Gancia",
                            "precio": 122
                        },
                        {
                            "id": 13,
                            "nombre": "Whisky",
                            "precio": 190
                        },
                        {
                            "id": 14,
                            "nombre": "Martini",
                            "precio": 142
                        },
                        {
                            "id": 15,
                            "nombre": "Jarra loca",
                            "precio": 200
                        }
                    ]
                }
            ]
        });
db.pedidos.drop();
db.pedidos.createIndex({"usuario_id":1});
