
'use client';

import { useState } from 'react';
import { handleApiCall, pingApi } from '../lib/api';

const API_BASE = process.env.NEXT_PUBLIC_API_BASE_URL || 'http://localhost:8080';

export default function Home() {
  const [result, setResult] = useState<string>('');
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  const ping = async () => {
    setLoading(true);
    setError(null);
    
    try {
      const json = await handleApiCall(pingApi);
      setResult(`status: ${json.status} | service: ${json.service}`);
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
      {error && <p style={{color: 'red'}}>{error}</p>}
      {result && <p style={{color: 'green'}}>{result}</p>}
    </main>
  );
}
