package com.linkgem.infrastructure.link.opengraph;

import com.linkgem.domain.link.opengraph.OpenGraph;

public interface OpenGraphReader {

    OpenGraph call(String url);
}
