
'use client';

import { useState } from 'react';

const API_BASE = process.env.NEXT_PUBLIC_API_BASE_URL || 'http://localhost:8080';

export default function Home() {
  const [result, setResult] = useState<string>('');

  const ping = async () => {
    try {
      const res = await fetch(`${API_BASE}/api/ping`);
      const json = await res.json();
      setResult(`status: ${json.status}`);
    } catch (e) {
      setResult('error');
    }
  };

  return (
    <main style={{ padding: 24, fontFamily: 'sans-serif' }}>
      <h1>Spring Boot CRUD Template</h1>
      <p>API Base: {API_BASE}</p>
      <button onClick={ping}>Ping API</button>
      <pre id="result">{result}</pre>
    </main>
  );
}
