# Arquitetura

## 1. Persistência local: DataStore

Entre **DataStore**, **Room** e **SQLDelight**, escolhemos o **DataStore** para guardar os dados no aparelho.

O Supabase já é o banco principal do aplicativo. No aparelho, precisamos apenas guardar o último retorno das quatro listas (relatos, avaliações, pensamentos e higiene do sono) para exibir quando não houver conexão. Room e SQLDelight exigiriam criação de tabelas, migrações e configuração extra por plataforma para algo que não precisa de consultas SQL locais.

Com o DataStore, reaproveitamos os mesmos DTOs com `kotlinx.serialization` já usados na API, gravamos de forma assíncrona e rodamos o mesmo código no Android e no Desktop. Se no futuro precisarmos de buscas locais ou fila de escrita offline, basta trocar a implementação atrás das interfaces de cache.
