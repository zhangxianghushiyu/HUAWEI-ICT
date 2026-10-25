/**
 * 隐私同意标志模块的单元测试（Node 内置 test runner，无第三方依赖）
 * 运行：node --test tests/
 */
const test = require('node:test');
const assert = require('node:assert');

// 模拟 uni 存储（内存版）
function createUniStub(initial = {}) {
  const store = { ...initial };
  return {
    getStorageSync(key) {
      return Object.prototype.hasOwnProperty.call(store, key) ? store[key] : '';
    },
    setStorageSync(key, value) {
      store[key] = value;
    },
    _store: store
  };
}

function loadModule() {
  const path = require('path');
  const mod = require(path.join(__dirname, '..', 'common', 'privacy-consent.js'));
  return mod;
}

test('未设置同意标志时 isPrivacyAgreed() 返回 false', () => {
  global.uni = createUniStub();
  const mod = loadModule();
  assert.strictEqual(mod.isPrivacyAgreed(), false);
});

test('标志被显式设置为 false 时 isPrivacyAgreed() 返回 false', () => {
  global.uni = createUniStub({ privacyPolicyAgreed: false });
  const mod = loadModule();
  assert.strictEqual(mod.isPrivacyAgreed(), false);
});

test('标志为 true 时 isPrivacyAgreed() 返回 true', () => {
  global.uni = createUniStub({ privacyPolicyAgreed: true });
  const mod = loadModule();
  assert.strictEqual(mod.isPrivacyAgreed(), true);
});

test('setPrivacyAgreed() 会持久化同意标志并可再读取', () => {
  global.uni = createUniStub();
  const mod = loadModule();
  mod.setPrivacyAgreed();
  assert.strictEqual(mod.isPrivacyAgreed(), true);
  assert.strictEqual(global.uni._store.privacyPolicyAgreed, true);
});

test('uni 不可用时（如 Node 环境）两个方法均安全降级不抛异常', () => {
  global.uni = undefined;
  const mod = loadModule();
  assert.strictEqual(mod.isPrivacyAgreed(), false);
  assert.doesNotThrow(() => mod.setPrivacyAgreed());
});
