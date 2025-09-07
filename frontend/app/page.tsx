
'use client';

import { useState } from 'react';
import { jsonFetch } from '../lib/jsonFetch';

export default function Home() {
  const [result, setResult] = useState<string>('');
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  const ping = async () => {
    setLoading(true);
    setError(null);
    
    try {
      const json = await jsonFetch("/api/ping"); // uses Next.js dev proxy
      setResult(`status: ${json.status}`);
    } catch (e) {
      setError('Backend connection failed');
      setResult('');
    } finally {
      setLoading(false);
    }
  };

  return (
    <main style={{ padding: 24, fontFamily: 'sans-serif' }}>
      <h1>CodeForge Platform</h1>
      <p style={{ color: '#666', fontSize: '14px', marginTop: '8px' }}>Enterprise Full-Stack Development Platform</p>
      <p>API Base: {API_BASE}</p>
      <button onClick={ping} disabled={loading}>
        {loading ? 'Checking...' : 'Ping API'}
      </button>
      {loading && <p>Checking backend connection...</p>}
      {error && <p style={{ color: "crimson" }}>{error}</p>}
      {result && <p style={{ color: "seagreen" }}>{result}</p>}
    </main>
  );
}
