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
            "longitud": -60.699605
        });
db.restaurantes.insert({
            "id": 2,
            "nombre": "Paladar Negro",
            "moneda": {
                "moneda": "$"
            },
            "latitud": -31.6387288,
            "longitud": -60.6936089
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
                    "nombre": "Entradas",
                    "productos": [
                        {
                            "id": 1,
                            "nombre": "Empanadas de carne",
                            "precio": 12
                        },
                        {
                            "id": 2,
                            "nombre": "Empanadas de verdura",
                            "precio": 23
                        },
                        {
                            "id": 3,
                            "nombre": "Empanadas árabes",
                            "precio": 34
                        },
                        {
                            "id": 4,
                            "nombre": "Ensalada",
                            "precio": 38
                        },
                        {
                            "id": 5,
                            "nombre": "Papas fritas",
                            "precio": 86
                        },
                        {
                            "id": 6,
                            "nombre": "Empanadas de carne",
                            "precio": 12
                        },
                        {
                            "id": 7,
                            "nombre": "Empanadas de verdura",
                            "precio": 23
                        },
                        {
                            "id": 8,
                            "nombre": "Empanadas árabes",
                            "precio": 34
                        },
                        {
                            "id": 9,
                            "nombre": "Ensalada",
                            "precio": 38
                        },
                        {
                            "id": 10,
                            "nombre": "Papas fritas",
                            "precio": 86
                        },
                        {
                            "id": 11,
                            "nombre": "Empanadas de carne",
                            "precio": 12
                        },
                        {
                            "id": 12,
                            "nombre": "Empanadas de verdura",
                            "precio": 23
                        },
                        {
                            "id": 13,
                            "nombre": "Empanadas árabes",
                            "precio": 34
                        },
                        {
                            "id": 14,
                            "nombre": "Ensalada",
                            "precio": 38
                        },
                        {
                            "id": 15,
                            "nombre": "Papas fritas",
                            "precio": 86
                        }
                    ]
                },
                {
                    "id": 2,
                    "nombre": "Sandwiches",
                    "productos": [
                        {
                            "id": 1,
                            "nombre": "Sandwiches",
                            "precio": 12
                        },
                        {
                            "id": 2,
                            "nombre": "Triples",
                            "precio": 67
                        },
                        {
                            "id": 3,
                            "nombre": "Jamon cocido",
                            "precio": 24
                        },
                        {
                            "id": 4,
                            "nombre": "Sandwich con ananá",
                            "precio": 56
                        },
                        {
                            "id": 5,
                            "nombre": "Sandwiches",
                            "precio": 65
                        },
                        {
                            "id": 6,
                            "nombre": "Triples",
                            "precio": 67
                        },
                        {
                            "id": 7,
                            "nombre": "Jamón cocido",
                            "precio": 24
                        },
                        {
                            "id": 8,
                            "nombre": "Sandwich con ananá",
                            "precio": 24
                        },
                        {
                            "id": 9,
                            "nombre": "Sandwiches",
                            "precio": 14
                        },
                        {
                            "id": 10,
                            "nombre": "Triples",
                            "precio": 67
                        },
                        {
                            "id": 11,
                            "nombre": "Jamón cocido",
                            "precio": 24
                        },
                        {
                            "id": 12,
                            "nombre": "Sandwich con ananá",
                            "precio": 24
                        },
                        {
                            "id": 13,
                            "nombre": "Sandwiches",
                            "precio": 12
                        },
                        {
                            "id": 14,
                            "nombre": "Triples",
                            "precio": 67
                        },
                        {
                            "id": 15,
                            "nombre": "Jamón cocido",
                            "precio": 24
                        },
                        {
                            "id": 16,
                            "nombre": "Sandwich con ananá",
                            "precio": 78
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
                            "precio": 172
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
