-- ==============================================================================
-- Script DDL para Supabase (PostgreSQL) - Tabela: avaliacoes
-- Entidade: Avaliação de Sentimentos / Estado Emocional
-- ==============================================================================

-- 1. Criação da tabela avaliacoes
CREATE TABLE IF NOT EXISTS public.avaliacoes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    data_registro TEXT NOT NULL,
    intensidade INTEGER NOT NULL CHECK (intensidade >= 1 AND intensidade <= 10),
    descricao TEXT NOT NULL,
    gatilhos TEXT[] NOT NULL DEFAULT '{}',
    created_at TIMESTAMPTZ DEFAULT now()
);

-- 2. Habilitação do Row Level Security (RLS)
ALTER TABLE public.avaliacoes ENABLE ROW LEVEL SECURITY;

-- 3. Políticas de acesso para o client (anon / public key)

-- Política de Leitura (SELECT)
CREATE POLICY "Permitir leitura anon e autenticada de avaliacoes"
ON public.avaliacoes
FOR SELECT
TO anon, authenticated
USING (true);

-- Política de Inserção (INSERT)
CREATE POLICY "Permitir insercao anon e autenticada de avaliacoes"
ON public.avaliacoes
FOR INSERT
TO anon, authenticated
WITH CHECK (true);

-- Política de Atualização (UPDATE) - opcional caso precise editar futuramente
CREATE POLICY "Permitir atualizacao anon e autenticada de avaliacoes"
ON public.avaliacoes
FOR UPDATE
TO anon, authenticated
USING (true)
WITH CHECK (true);

-- Política de Remoção (DELETE) - opcional caso precise deletar futuramente
CREATE POLICY "Permitir remocao anon e autenticada de avaliacoes"
ON public.avaliacoes
FOR DELETE
TO anon, authenticated
USING (true);
